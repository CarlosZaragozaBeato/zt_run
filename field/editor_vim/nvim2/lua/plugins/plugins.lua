return {

  -- ==============================
  -- ⚡ AUTOCOMPLETADO
  -- ==============================
  {
    "hrsh7th/nvim-cmp",
    dependencies = {
      "hrsh7th/cmp-nvim-lsp",
      "hrsh7th/cmp-buffer",
      "hrsh7th/cmp-path",
      "L3MON4D3/LuaSnip",
    },
  },

  -- ==============================
  -- 🧠 LSP (Lenguajes)
  -- ==============================
  {
    "neovim/nvim-lspconfig",
  },

  {
    "williamboman/mason.nvim",
    config = true,
  },

  {
    "williamboman/mason-lspconfig.nvim",
  },

  -- ==============================
  -- 🌳 TREESITTER (Sintaxis)
  -- ==============================
  {
    "nvim-treesitter/nvim-treesitter",
    build = ":TSUpdate",
  },

  -- ==============================
  -- 🗂️ ARCHIVOS
  -- ==============================
  {
    "nvim-tree/nvim-tree.lua",
    dependencies = { "nvim-tree/nvim-web-devicons" },
    config = true,
  },

  -- ==============================
  -- 🔎 BUSCADOR FUZZY
  -- ==============================
  {
    "nvim-telescope/telescope.nvim",
    dependencies = { "nvim-lua/plenary.nvim" },
  },

  -- ==============================
  -- 🛠️ FORMATO AUTOMÁTICO
  -- ==============================
  {
    "stevearc/conform.nvim",
  },

  -- ==============================
  -- 🐞 DEBUG (DAP)
  -- ==============================
  {
    "mfussenegger/nvim-dap",
  },

  {
    "rcarriga/nvim-dap-ui",
    dependencies = "mfussenegger/nvim-dap",
    config = true,
  },

  -- ==============================
  -- ✅ TESTING
  -- ==============================
  {
    "nvim-neotest/neotest",
    dependencies = {
      "nvim-lua/plenary.nvim",
      "nvim-treesitter/nvim-treesitter",
      "antoinemadec/FixCursorHold.nvim",
    },
  },

  -- ==============================
  -- 🌍 GIT
  -- ==============================
  {
    "lewis6991/gitsigns.nvim",
    config = true,
  },

  {
    "tpope/vim-fugitive",
  },

  -- ==============================
  -- 📦 PYTHON
  -- ==============================
  {
    "mfussenegger/nvim-dap-python",
  },

  -- ==============================
  -- 🐹 GO
  -- ==============================
  {
    "ray-x/go.nvim",
    dependencies = {
      "ray-x/guihua.lua",
      "neovim/nvim-lspconfig",
      "nvim-treesitter/nvim-treesitter",
    },
    config = true,
  },

  -- ==============================
  -- ☕ JAVA
  -- ==============================
  {
    "mfussenegger/nvim-jdtls",
  },

  -- ==============================
  -- 🚀 MEJOR UX
  -- ==============================
  {
    "folke/which-key.nvim",
    config = true,
  },

  {
    "windwp/nvim-autopairs",
    config = true,
  },

  {
    "numToStr/Comment.nvim",
    config = true,
  },
}
