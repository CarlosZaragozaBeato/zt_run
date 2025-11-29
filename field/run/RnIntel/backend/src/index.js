require("dotenv").config();
const Fastify = require("fastify");
const app = Fastify({ logger: true });

app.register(require("@fastify/cors"));
app.register(require("./routes/example"));

const AUTHORIZATION_URL = "https://flow.polar.com/oauth2/authorization";
const ACCESS_TOKEN_URL = "https://polarremote.com/v2/oauth2/token";
const ACCESSLINK_URL = "https://www.polaraccesslink.com/v3";

function getAuthorizationUrl({
	clientId,
	authorizationUrl,
	redirectUrl = null,
	responseType = "code",
}) {
	const params = new URLSearchParams({
		client_id: clientId,
		response_type: responseType,
	});

	if (redirectUrl) {
		params.append("redirect_uri", redirectUrl);
	}
	return `${authorizationUrl}?${params.toString()}`;
}

const url = getAuthorizationUrl({
	clientId: "tu_client_id",
	authorizationUrl: "https://example.com/oauth/authorize",
});
app.listen({ port: process.env.PORT || 3000 }, (err, address) => {
	if (err) {
		app.log.error(err);
		process.exit(1);
	}
	console.log(`Servidor escuchando en ${address}`);
});
