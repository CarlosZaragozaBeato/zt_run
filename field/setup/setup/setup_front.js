// setup-frontend.js
const { execSync } = require("child_process");
const fs = require("fs");
const path = require("path");
const readline = require("readline");

async function setupFrontend(rootPath) {
	const frontendPath = path.join(rootPath, "frontend");

	console.log("⚛ Inicializando frontend con Vite + React...");
	execSync(`npm create vite@latest frontend -- --template react`, {
		cwd: rootPath,
		stdio: "inherit",
	});

	process.chdir(frontendPath);

	console.log("📦 Instalando dependencias base...");
	execSync(`npm install`, { stdio: "inherit" });

	execSync(`npm install -D tailwindcss postcss autoprefixer`, {
		stdio: "inherit",
	});

	fs.writeFileSync(
		"tailwind.config.js",
		`
module.exports = {
  content: ["./index.html", "./src/**/*.{js,jsx}"],
  theme: {
    extend: {},
  },
  plugins: [],
};`.trim() + "\n",
	);

	fs.writeFileSync(
		"postcss.config.js",
		`
module.exports = {
  plugins: {
    tailwindcss: {},
    autoprefixer: {},
  },
};`.trim() + "\n",
	);

	fs.writeFileSync(
		path.join("src", "index.css"),
		`
@tailwind base;
@tailwind components;
@tailwind utilities;
`.trim() + "\n",
	);

	console.log("✅ Tailwind configurado.");

	console.log("Configuracion de Carpetas");

	const folders = [
		"assets",
		"components",
		"features",
		"hooks",
		"layouts",
		"lib",
		"pages",
		"router",
		"store",
		"styles",
	];

	folders.forEach((folder) => {
		const folderPath = path.join("src", folder);
		if (!fs.existsSync(folderPath)) {
			fs.mkdirSync(folderPath);
			console.log(`📁 Carpeta creada: src/${folder}`);
		}
	});

	console.log("✅ Frontend listo.");
}

module.exports = { setupFrontend };
