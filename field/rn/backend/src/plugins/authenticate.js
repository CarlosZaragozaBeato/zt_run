// backend/src/plugins/authenticate.js
const fp = require("fastify-plugin");
const jwt = require("jsonwebtoken");

async function authenticatePlugin(fastify) {
  fastify.decorate("authenticate", async function (request, reply) {
    const authHeader = request.headers.authorization;

    if (!authHeader || !authHeader.startsWith("Bearer ")) {
      reply.status(401).send({ error: "Token requerido" });
      return;
    }
    
    try {
      const token = authHeader.split(" ")[1];
      const decoded = jwt.verify(token, process.env.JWT_SECRET);
      request.user = decoded;
    } catch (err) {
      reply.status(401).send({ error: "Token inválido" });
    }
  });
}

module.exports = fp(authenticatePlugin);
