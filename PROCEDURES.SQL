
DELIMITER $$
CREATE PROCEDURE sp_registrar_entrada_parqueo1(
    IN p_dni VARCHAR(20),
    IN p_nombres VARCHAR(100),
    IN p_ape_paterno VARCHAR(100),
    IN p_ape_materno VARCHAR(100),
    IN p_telefono VARCHAR(20),
    IN p_direccion TEXT,
    IN p_correo VARCHAR(100),
    IN p_placa VARCHAR(20),
    IN p_marca VARCHAR(50),
    IN p_color VARCHAR(30),
    IN p_id_tipo_vehiculo INT,
    IN p_id_espacio INT,
    IN p_tarifa_hora DECIMAL(10,2),
    IN p_ticket VARCHAR(20),
    IN p_hora_ingreso DATETIME,
    IN p_creado_por INT,
    IN p_creado_en DATETIME,
    IN p_actualizado_en DATETIME
)
BEGIN
    DECLARE v_cliente_id INT;
    DECLARE v_vehiculo_id INT;
    DECLARE v_registro_id INT;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    START TRANSACTION;

    -- Validar espacio disponible
    IF (SELECT ocupado FROM espacio WHERE id = p_id_espacio) = 1 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'ERR001';
    END IF;

    -- Crear o actualizar cliente
    SELECT id INTO v_cliente_id FROM cliente WHERE dni = p_dni LIMIT 1;
    IF v_cliente_id IS NULL THEN
        INSERT INTO cliente (nombres, ape_paterno, ape_materno, dni, telefono, direccion, correo, estado)
        VALUES (p_nombres, p_ape_paterno, p_ape_materno, p_dni, p_telefono, p_direccion, p_correo, 1);
        SET v_cliente_id = LAST_INSERT_ID();
    END IF;

    -- Crear o actualizar vehículo
    SELECT id INTO v_vehiculo_id FROM vehiculo WHERE placa = p_placa LIMIT 1;
    IF v_vehiculo_id IS NULL THEN
        INSERT INTO vehiculo (placa, marca, color, id_tipo_vehiculo, id_cliente, estado)
        VALUES (p_placa, p_marca, p_color, p_id_tipo_vehiculo, v_cliente_id, 1);
        SET v_vehiculo_id = LAST_INSERT_ID();
    END IF;
	--  Validar que el vehículo no tenga una entrada activa
    IF EXISTS (
        SELECT 1 FROM registro_parqueo
        WHERE id_vehiculo = v_vehiculo_id AND estado_pago = 'PENDIENTE'
    ) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'ERR003';
    END IF;
    -- Registrar entrada
    INSERT INTO registro_parqueo (
        ticket, id_vehiculo, id_espacio, id_cliente,
        hora_ingreso, tarifa_hora, estado_pago, creado_por,
        creado_en, actualizado_en
    ) VALUES (
        p_ticket, v_vehiculo_id, p_id_espacio, v_cliente_id,
        p_hora_ingreso, p_tarifa_hora, 'PENDIENTE', p_creado_por,
        p_creado_en, p_actualizado_en
    );
    SET v_registro_id = LAST_INSERT_ID();

    -- Marcar espacio como ocupado
    UPDATE espacio SET ocupado = 1 WHERE id = p_id_espacio;

    -- Devolver datos del registro
    SELECT
        rp.ticket,
        rp.hora_ingreso,
        rp.tarifa_hora,
        rp.monto_total,
        rp.estado_pago,
        c.nombres,
        c.ape_paterno,
        c.ape_materno,
        c.dni,
        c.telefono,
        v.placa,
        v.marca,
        v.color,
        c.correo AS email,
        tv.nombre AS tipo_vehiculo,
        z.nombre AS zona_nombre,
        e.numero_espacio
    FROM registro_parqueo rp
    JOIN cliente c ON c.id = rp.id_cliente
    JOIN vehiculo v ON v.id = rp.id_vehiculo
    JOIN espacio e ON e.id = rp.id_espacio
    JOIN tipo_vehiculo tv ON tv.id = v.id_tipo_vehiculo
    JOIN zona z ON z.id = e.id_zona
    WHERE rp.id = v_registro_id;

    COMMIT;
END$$

DELIMITER ;


CALL sp_registrar_entrada_parqueo(
    '72345678',             -- p_dni
    'Juan',                 -- p_nombres
    'Pérez',                -- p_ape_paterno
    'Ramírez',              -- p_ape_materno
    '987654321',            -- p_telefono
    'Av. Los Olivos 123',   -- p_direccion
    'juan@example.com',     -- p_correo
    'ABC-123',              -- p_placa
    'Toyota',               -- p_marca
    'Rojo',                 -- p_color
    1,                      -- p_id_tipo_vehiculo (asegúrate que exista en `tipo_vehiculo`)
    5,                      -- p_id_espacio (asegúrate que exista en `espacio`)
    5.50,                   -- p_tarifa_hora
    'TCKT-20250717-001',    -- p_ticket
    NOW(),                  -- p_hora_ingreso
    1,                      -- p_creado_por (ID del usuario que crea)
    NOW(),                  -- p_creado_en
    NOW()                   -- p_actualizado_en
);


-- Asegúrate de que las tablas y columnas referenciadas existen y son correctas.


DELIMITER $$
CREATE  PROCEDURE sp_registrar_salida_parqueo(
    IN p_ticket VARCHAR(20),
    IN p_hora_salida DATETIME,
    IN p_monto_total DECIMAL(10,2),
    IN p_id_metodo_pago INT,
    IN p_actualizado_por INT,
    IN p_actualizado_en DATETIME
)
BEGIN
    DECLARE v_registro_id INT;
    DECLARE v_espacio_id INT;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    START TRANSACTION;

    -- Obtener registro y validar
    SELECT id, id_espacio INTO v_registro_id, v_espacio_id
    FROM registro_parqueo
    WHERE ticket = p_ticket AND hora_salida IS NULL;

    IF v_registro_id IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'ERR002';
    END IF;

    -- Registrar salida
    UPDATE registro_parqueo SET
        hora_salida = p_hora_salida,
        monto_total = p_monto_total,
        estado_pago = 'PAGADO',
        id_metodo_pago = p_id_metodo_pago,
        actualizado_por = p_actualizado_por,
        actualizado_en = p_actualizado_en
    WHERE id = v_registro_id;

    -- Liberar espacio
    UPDATE espacio SET ocupado = 0 WHERE id = v_espacio_id;

    -- Devolver datos del registro
    SELECT
        rp.ticket,
        rp.hora_ingreso,
        rp.hora_salida,
        rp.tarifa_hora,
        rp.monto_total,
        c.nombres,
        c.ape_paterno,
        c.ape_materno,
        c.dni,
        c.telefono,
        v.placa,
        v.marca,
        v.color,
        tv.nombre AS tipo_vehiculo,
        z.nombre AS zona_nombre,
        e.numero_espacio
    FROM registro_parqueo rp
    JOIN cliente c ON c.id = rp.id_cliente
    JOIN vehiculo v ON v.id = rp.id_vehiculo
    JOIN espacio e ON e.id = rp.id_espacio
    JOIN tipo_vehiculo tv ON tv.id = v.id_tipo_vehiculo
    JOIN zona z ON z.id = e.id_zona
    WHERE rp.id = v_registro_id;

    COMMIT;
END$$

DELIMITER ;


CALL sp_registrar_salida_parqueo(
    'TCKT-20250717-001',    -- p_ticket (debe coincidir con uno generado al ingreso)
    NOW(),                  -- p_hora_salida
    11.00,                  -- p_monto_total (por ejemplo: 2 horas x S/ 5.50)
    1,                      -- p_id_metodo_pago (ID válido en tu tabla método de pago)
    1,                      -- p_actualizado_por (ID del usuario que realiza la salida)
    NOW()                   -- p_actualizado_en
);

-- Asegúrate de que las tablas y columnas referenciadas existen y son correctas.

DELIMITER $$
CREATE PROCEDURE sp_obtener_registro_activo_por_espacio(
    IN p_id_espacio INT
)
BEGIN
    SELECT
        rp.ticket AS ticket,
        rp.hora_ingreso AS horaIngreso,
        rp.tarifa_hora AS tarifaHora,
        rp.monto_total AS montoTotal,
        rp.estado_pago AS estadoPago,
        c.nombres AS nombres,
        c.ape_paterno AS apePaterno,
        c.ape_materno AS apeMaterno,
        c.dni AS dni,
        c.telefono AS telefono,
        v.placa AS placa,
        c.correo AS email,
        v.marca AS marca,
        v.color AS color,
        c.correo AS email,
        tv.nombre AS tipoVehiculo,
        z.nombre AS zonaNombre,
        e.numero_espacio AS numeroEspacio
    FROM registro_parqueo rp
    JOIN cliente c ON c.id = rp.id_cliente
    JOIN vehiculo v ON v.id = rp.id_vehiculo
    JOIN espacio e ON e.id = rp.id_espacio
    JOIN tipo_vehiculo tv ON tv.id = v.id_tipo_vehiculo
    JOIN zona z ON z.id = e.id_zona
    WHERE rp.id_espacio = p_id_espacio
      AND rp.hora_salida IS NULL
    LIMIT 1;
END$$

DELIMITER ;


CALL sp_obtener_registro_activo_por_espacio(5);
