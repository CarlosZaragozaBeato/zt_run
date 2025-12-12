return {
  -- === LSPs ===
  {
    "neovim/nvim-lspconfig",
    opts = {
      servers = {
        -- Java (requiere jdtls)
        jdtls = {},
        -- TypeScript / JavaScript
        tsserver = {},
        eslint = {},
        -- Python
        pyright = {},
        ruff_lsp = {},
        -- SQL
        sqlls = {},
        -- Docker
        dockerls = {},
        docker_compose_language_service = {},
      },
    },
  },

  -- === FORMATTERS + LINTERS ===
  {
    "stevearc/conform.nvim",
    opts = {
      formatters_by_ft = {
        java = { "google_java_format" },
        javascript = { "prettier" },
        typescript = { "prettier" },
        json = { "prettier" },
        yaml = { "prettier" },
        markdown = { "prettier" },
        python = { "black", "isort" },
        sql = { "sql-formatter" },
        dockerfile = { "hadolint" },
      },
    },
  },

  -- === DAP DEBUGGERS ===
  {
    "mfussenegger/nvim-dap",
    dependencies = {
      "jay-babu/mason-nvim-dap.nvim",
      "rcarriga/nvim-dap-ui",
    },
    opts = {
      ensure_installed = {
        "java-debug-adapter",
        "java-test",
        "js-debug-adapter",
        "python",
      },
    },
  },

  -- === EXTRAS ÚTILES PARA LENGUAJES ===

  -- Java: integración con jdtls
  {
    "mfussenegger/nvim-jdtls",
  },

  -- TypeScript: utilidades extra como rename de imports, fixes…
  {
    "jose-elias-alvarez/typescript.nvim",
    config = function()
      require("typescript").setup({})
    end,
  },

  -- Python: Ruff para linting ultra rápido
  {
    "astral-sh/ruff-lsp",
  },

  -- SQL: resaltado extendido
  {
    "nanotee/sqls.nvim",
  },

  -- Docker: sintaxis mejorada
  {
    "ekalinin/Dockerfile.vim",
  },
}
