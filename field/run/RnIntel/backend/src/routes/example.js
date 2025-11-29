module.exports = async function (fastify, opts) {
  fastify.get("/", async (request, reply) => {
    return { message: "API backend funcionando 🚀" };
  });
};
