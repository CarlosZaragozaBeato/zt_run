local map = vim.keymap.set
local opts = { silent = true }

-- ======================================================
-- 🌍 GENERAL
-- ======================================================
map("n", "<leader>w", "<cmd>w<cr>", { desc = "Guardar archivo" })
map("n", "<leader>q", "<cmd>q<cr>", { desc = "Cerrar ventana" })
map("n", "<leader>Q", "<cmd>qa!<cr>", { desc = "Salir forzado" })

-- ======================================================
-- 📂 ARCHIVOS (TELESCOPE)
-- ======================================================
map("n", "<leader>ff", "<cmd>Telescope find_files<cr>", { desc = "Buscar archivos" })
map("n", "<leader>fg", "<cmd>Telescope live_grep<cr>", { desc = "Buscar texto" })
map("n", "<leader>fb", "<cmd>Telescope buffers<cr>", { desc = "Buscar buffers" })
map("n", "<leader>fh", "<cmd>Telescope help_tags<cr>", { desc = "Buscar ayuda" })

-- ======================================================
-- 🗂️ EXPLORADOR
-- ======================================================
map("n", "<leader>e", "<cmd>NvimTreeToggle<cr>", { desc = "Explorador de archivos" })

-- ======================================================
-- 📑 BUFFERS (LO MÁS USADO)
-- ======================================================
map("n", "<leader>bc", "<cmd>bd<cr>", { desc = "Cerrar buffer" })
map("n", "<leader>bo", "<cmd>%bd|e#|bd#<cr>", { desc = "Cerrar otros buffers" })
map("n", "<leader>bl", "<cmd>bnext<cr>", { desc = "Siguiente buffer" })
map("n", "<leader>bh", "<cmd>bprevious<cr>", { desc = "Buffer anterior" })

-- ======================================================
-- 🧾 TABS (OPCIONAL)
-- ======================================================
map("n", "<leader>tn", "<cmd>tabnew<cr>", { desc = "Nuevo tab" })
map("n", "<leader>tc", "<cmd>tabclose<cr>", { desc = "Cerrar tab" })
map("n", "<leader>to", "<cmd>tabonly<cr>", { desc = "Cerrar otros tabs" })
map("n", "<leader>tl", "<cmd>tabnext<cr>", { desc = "Siguiente tab" })
map("n", "<leader>th", "<cmd>tabprevious<cr>", { desc = "Tab anterior" })

-- ======================================================
-- 🧠 LSP
-- ======================================================
map("n", "gd", vim.lsp.buf.definition, { desc = "Ir a definición" })
map("n", "gD", vim.lsp.buf.declaration, { desc = "Declaración" })
map("n", "gr", vim.lsp.buf.references, { desc = "Referencias" })
map("n", "gi", vim.lsp.buf.implementation, { desc = "Implementación" })
map("n", "K", vim.lsp.buf.hover, { desc = "Documentación" })
map("n", "<leader>ca", vim.lsp.buf.code_action, { desc = "Code Action" })
map("n", "<leader>cf", vim.lsp.buf.format, { desc = "Formatear código" })
map("n", "<leader>rn", vim.lsp.buf.rename, { desc = "Renombrar" })

-- ======================================================
-- 🛠️ DIAGNÓSTICOS
-- ======================================================
map("n", "[d", vim.diagnostic.goto_prev, { desc = "Error anterior" })
map("n", "]d", vim.diagnostic.goto_next, { desc = "Siguiente error" })
map("n", "<leader>dd", vim.diagnostic.open_float, { desc = "Ver error" })

-- ======================================================
-- ✅ TESTING (NEOTEST)
-- ======================================================
map("n", "<leader>tt", function()
  require("neotest").run.run()
end, { desc = "Test actual" })

map("n", "<leader>tf", function()
  require("neotest").run.run(vim.fn.expand("%"))
end, { desc = "Test archivo" })

map("n", "<leader>ts", function()
  require("neotest").summary.toggle()
end, { desc = "Resumen tests" })

-- ======================================================
-- 🐞 DEBUG (DAP)
-- ======================================================
map("n", "<F5>", function()
  require("dap").continue()
end, { desc = "Debug iniciar" })
map("n", "<F10>", function()
  require("dap").step_over()
end, { desc = "Step over" })
map("n", "<F11>", function()
  require("dap").step_into()
end, { desc = "Step into" })
map("n", "<F12>", function()
  require("dap").step_out()
end, { desc = "Step out" })

map("n", "<leader>db", function()
  require("dap").toggle_breakpoint()
end, { desc = "Breakpoint" })
map("n", "<leader>dr", function()
  require("dap").repl.open()
end, { desc = "Debug REPL" })

-- ======================================================
-- 🌿 GIT
-- ======================================================
map("n", "<leader>gs", "<cmd>Git<cr>", { desc = "Git status" })
map("n", "<leader>gc", "<cmd>Git commit<cr>", { desc = "Git commit" })
map("n", "<leader>gp", "<cmd>Git push<cr>", { desc = "Git push" })

-- ======================================================
-- 🧮 GO
-- ======================================================
map("n", "<leader>gr", "<cmd>GoRun<cr>", { desc = "Go Run" })
map("n", "<leader>gt", "<cmd>GoTest<cr>", { desc = "Go Test" })

-- ======================================================
-- 🐍 PYTHON
-- ======================================================
map("n", "<leader>pr", "<cmd>!python %<cr>", { desc = "Python Run" })

-- ======================================================
-- ☕ JAVA
-- ======================================================
map("n", "<leader>jr", "<cmd>!mvn spring-boot:run<cr>", { desc = "Run Spring Boot" })

-- ======================================================
-- 🖥️ TERMINAL
-- ======================================================
map("n", "<leader>tt", "<cmd>terminal<cr>", { desc = "Abrir terminal" })
map("t", "<Esc>", "<C-\\><C-n>", { desc = "Salir del terminal" })

-- ======================================================
-- ✨ EXTRAS
-- ======================================================
map("n", "<leader>/", function()
  require("Comment.api").toggle.linewise.current()
end, { desc = "Comentar línea" })

map("v", "<leader>/", function()
  require("Comment.api").toggle.linewise(vim.fn.visualmode())
end, { desc = "Comentar selección" })
