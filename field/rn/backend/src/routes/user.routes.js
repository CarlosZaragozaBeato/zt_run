const { PrismaClient } = require("@prisma/client");

const prisma = new PrismaClient();

module.exports = async function (fastify) {
  // Exchange code for token
  fastify.get("/user", {
    preHandler: fastify.authenticate,
    handler: async (request, reply) => {
      const userId = request.user?.id;
        if (!userId){
          return reply.status(401).send({error: "Unauthorized"});
        }
        polarAdapter.test(userId);        
    },
  });
};
