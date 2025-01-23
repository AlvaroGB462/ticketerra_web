<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Ticketerra</title>
<link rel="icon" href="/static/imagenes/LogoFavicon.jpg" />
<!-- Bootstrap CSS v5.2.1 -->
<link
      href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css"
      rel="stylesheet"
    />
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.min.js"></script>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
      crossorigin="anonymous"
    />
<link href="/static/css/stylesRegistrar.css" rel="stylesheet">
</head>
<body>
	<header class="header">
		<div class="logo">
			<a href="/usuarios/index"> <img
				src="/static/imagenes/LogoTipo.png" alt="Logo" />
			</a>
			<h1>Ticketerra</h1>
		</div>
		<button class="hamburger" aria-label="Toggle navigation">
			<span></span> <span></span> <span></span>
		</button>
		<nav class="nav">
			<a href="/usuarios/index" class="nav-link" id="link-home">Inicio</a>
			<a href="verTickets.html" class="nav-link" id="link-tickets">Mis
				Tickets</a> <a href="crearTicket.html" class="nav-link" id="link-create">Crear
				Ticket</a> <a class="nav-link bi bi-person-circle"
				href="/usuarios/login" id="link-login"></a>
		</nav>
	</header>
	<div class="container">
		<div id="contenedor-seleccion">
			<!-- Selección Inicial -->
			<div class="caja-registro">
				<h3 class="titulo-registro">Selecciona tu Registro</h3>
				<div class="d-flex justify-content-around"
					style="padding-top: 10px; padding-bottom: 10px;">
					<button class="btn" onclick="mostrarFormulario("gratuito")">Registro
						Gratuito</button>
					<button class="btn" onclick="mostrarFormulario("premium")">Registro
						Premium</button>
				</div>
				<p class="info-premium">
					Con el <strong>Registro Premium</strong> tendrás preferencias en la
					respuesta de los tickets de soporte, los cuales serán atendidos con
					prioridad frente a los usuarios gratuitos.
				</p>
			</div>
		</div>

		<div id="contenedor-formulario"></div>
	</div>

	<!-- Bootstrap JS -->
	<script src="/static/js/scripts.js"></script>
</body>
</html>
