<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Ticketerra - Recuperar Contraseña</title>
    <link rel="icon" href="/static/imagenes/LogoFavicon.jpg" />
    <link rel="stylesheet" href="/static/css/stylesRecuperar.css" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet" />
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body>
    <main>
        <div class="form-container">
            <p class="title">Recuperar Contraseña</p>

            <!-- Mostrar mensaje de error si existe en el modelo -->
            <c:if test="${not empty mensaje}">
                <div class="alert alert-info" role="alert">
                    ${mensaje}
                </div>
            </c:if>

            <form id="recover-form" class="form" action="/usuarios/recuperar" method="post">
                <div class="input-group">
                    <label for="correo">Correo</label>
                    <input type="email" name="correo" id="correo" placeholder="Introduce tu correo" required>
                </div>
                <button type="submit" class="sign">Enviar enlace de recuperación</button>
            </form>

            <p class="signup">
                ¿Recuperaste tu contraseña? <a href="/usuarios/login">Inicia sesión</a>
            </p>
        </div>
    </main>
</body>
</html>
