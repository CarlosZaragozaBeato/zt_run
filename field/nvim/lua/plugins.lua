-- ~/.config/nvim/lua/plugins.lua

-- Verifica e instala lazy.nvim si no está presente
local lazypath = vim.fn.stdpath("data") .. "/lazy/lazy.nvim"
if not vim.loop.fs_stat(lazypath) then
  vim.fn.system({
    "git",
    "clone",
    "--filter=blob:none",
    "https://github.com/folke/lazy.nvim.git",
    "--branch=stable", -- última versión estable
    lazypath,
  })
end
vim.opt.rtp:prepend(lazypath)

-- Configura los plugins
require("lazy").setup({
  -- Explorador de archivos
  { "nvim-tree/nvim-tree.lua" },
  { "nvim-tree/nvim-web-devicons" },

  -- Buscador con Telescope
  { "nvim-telescope/telescope.nvim", dependencies = { "nvim-lua/plenary.nvim" } },

  -- Soporte LSP
  { "neovim/nvim-lspconfig" },

  -- Autocompletado
  { "hrsh7th/nvim-cmp" },
  { "hrsh7th/cmp-nvim-lsp" },
  { "L3MON4D3/LuaSnip" },

  -- 🎨 Tema visual (activo: catppuccin)
  {
    "catppuccin/nvim",
    name = "catppuccin",
    lazy = false,
    priority = 1000,
    config = function()
      vim.cmd.colorscheme "catppuccin-macchiato"
    end,
  },

  -- Linters y formatters con none-ls.nvim
  {
    "nvimtools/none-ls.nvim",
    dependencies = { "nvim-lua/plenary.nvim" },
    config = function()
      local null_ls = require("null-ls")

      null_ls.setup({
        sources = {
          -- JavaScript / TypeScript
          null_ls.builtins.diagnostics.eslint_d,
          null_ls.builtins.formatting.prettier,

          -- Python
          null_ls.builtins.diagnostics.ruff,
          null_ls.builtins.formatting.black,
        },
      })
    end,
  },

  -- Otros temas (desactivados, descomenta si quieres probarlos)

  -- {
  --   "folke/tokyonight.nvim",
  --   lazy = false,
  --   priority = 1000,
  --   config = function()
  --     vim.cmd.colorscheme "tokyonight-night"
  --   end,
  -- },

  -- {
  --   "morhetz/gruvbox",
  --   lazy = false,
  --   priority = 1000,
  --   config = function()
  --     vim.cmd.colorscheme "gruvbox"
  --   end,
  -- },

  -- {
  --   "navarasu/onedark.nvim",
  --   lazy = false,
  --   priority = 1000,
  --   config = function()
  --     require("onedark").setup {
  --       style = "deep" -- opciones: dark, cool, deep, warm, warmer
  --     }
  --     require("onedark").load()
  --   end,
  -- },
})

