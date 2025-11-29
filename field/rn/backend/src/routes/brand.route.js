const { registerBrandAccount } = require("../services/api_device.service");
const { PrismaClient } = require("@prisma/client");

const prisma = new PrismaClient();

module.exports = async function (fastify) {
  fastify.post("/brand/register", {
    preHandler: fastify.authenticate,
    handler: async (request, reply) => {
      try {
        const { email, password, brand } = request.body;

        const account = await registerBrandAccount(email, password, brand);
        reply.send({ success: true, account });
      } catch (err) {
        reply.status(400).send({ error: err.message });
      }
    },
  });
};
