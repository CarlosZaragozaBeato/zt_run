-- ~/.config/nvim/lua/keymaps.lua

vim.g.mapleader = " "
local keymap = vim.keymap.set

-- Explorador de archivos (nvim-tree)
keymap("n", "<leader>e", ":NvimTreeToggle<CR>", { desc = "Toggle file explorer" })

-- Telescope - búsqueda de archivos, buffers, contenido, ayuda
keymap("n", "<leader>ff", ":Telescope find_files<CR>", { desc = "Find files" })
keymap("n", "<leader>fg", ":Telescope live_grep<CR>", { desc = "Live grep" })
keymap("n", "<leader>fb", ":Telescope buffers<CR>", { desc = "Find buffers" })
keymap("n", "<leader>fh", ":Telescope help_tags<CR>", { desc = "Help tags" })

-- Telescope - símbolos LSP en documento
keymap("n", "<leader>fs", ":Telescope lsp_document_symbols<CR>", { desc = "Document symbols" })

-- Navegación básica entre buffers
keymap("n", "<S-l>", ":bnext<CR>", { desc = "Next buffer" })
keymap("n", "<S-h>", ":bprevious<CR>", { desc = "Previous buffer" })

-- Cerrar buffer actual
keymap("n", "<leader>bd", ":bd<CR>", { desc = "Close buffer" })

-- Terminal en ventana actual
keymap("n", "<leader>t", ":terminal<CR>", { desc = "Open terminal" })

-- Terminal en split horizontal
keymap("n", "<leader>th", ":split | terminal<CR>", { desc = "Open terminal in horizontal split" })

-- Terminal en split vertical
keymap("n", "<leader>tv", ":vsplit | terminal<CR>", { desc = "Open terminal in vertical split" })

