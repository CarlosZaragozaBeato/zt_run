const { execSync } = require("child_process");
const fs = require("fs");
const path = require("path");

async function setupBackend(rootPath) {
	const backendPath = path.join(rootPath, "backend");

	console.log("🚀 Inicializando backend con Fastify...");
	fs.mkdirSync(backendPath, { recursive: true });
	process.chdir(backendPath);

	execSync("npm init -y", { stdio: "inherit" });

	console.log("📦 Instalando dependencias...");
	execSync(
		`npm install fastify fastify-cors fastify-env fastify-plugin dotenv prisma jsonwebtoken bcrypt zod nodemailer bullmq ioredis`,
		{ stdio: "inherit" },
	);

	execSync(`npx prisma init`, { stdio: "inherit" });

	console.log("📁 Creando estructura de carpetas...");

	const dirs = ["src", "src/routes", "src/plugins", "src/services", "src/jobs"];
	dirs.forEach((dir) =>
		fs.mkdirSync(path.join(backendPath, dir), { recursive: true }),
	);

	fs.writeFileSync(
		path.join(backendPath, ".gitignore"),
		`node_modules
.env
`,
	);

	fs.writeFileSync(
		path.join(backendPath, ".env"),
		`PORT=3000
JWT_SECRET=supersecret
DATABASE_URL="file:./dev.db"
REDIS_URL=redis://localhost:6379
`,
	);

	fs.writeFileSync(
		path.join(backendPath, "src", "index.js"),
		`require("dotenv").config();
const Fastify = require("fastify");
const app = Fastify({ logger: true });

app.register(require("@fastify/cors"));
app.register(require("./routes/example"));

app.listen({ port: process.env.PORT || 3000 }, (err, address) => {
  if (err) {
    app.log.error(err);
    process.exit(1);
  }
  console.log(\`Servidor escuchando en \${address}\`);
});
`,
	);

	fs.writeFileSync(
		path.join(backendPath, "src", "routes", "example.js"),
		`module.exports = async function (fastify, opts) {
  fastify.get("/", async (request, reply) => {
    return { message: "API backend funcionando 🚀" };
  });
};`,
	);

	fs.writeFileSync(
		path.join(backendPath, "README.md"),
		`# 🚀 Backend con Fastify

Stack:

- Fastify
- Prisma
- JWT
- bcrypt
- zod
- dotenv
- Nodemailer
- BullMQ + Redis

## 🧪 Comenzar

\`\`\`bash
npm install
npx prisma generate
npm run dev
\`\`\`

## 🌐 Ruta de prueba

\`GET /\` → Devuelve un mensaje básico.

`,
	);

	console.log("✅ Backend creado en:", backendPath);
}

module.exports = { setupBackend };
