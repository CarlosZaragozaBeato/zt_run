require("dotenv").config();
const formBody = require("@fastify/formbody");
const Fastify = require("fastify");
const app = Fastify({ logger: true });

app.register(require("@fastify/cors"));
app.register(formBody); 

app.register(require("./plugins/authenticate")).after(() => {
  console.log("authenticate hook:", typeof app.authenticate);
});

app.register(require("./routes/auth.routes"));
app.register(require("./routes/polar.routes"));
app.register(require("./routes/status.routes"));
app.register(require("./routes/token.routes"));
app.register(require("./routes/test.routes"));

app.listen({ port: process.env.PORT || 3000 }, (err, address) => {
  if (err) {
    app.log.error(err);
    process.exit(1);
  }
  console.log(`Servidor escuchando en ${address}`);
});
