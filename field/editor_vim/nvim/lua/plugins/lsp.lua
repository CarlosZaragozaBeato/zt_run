return {
  {
    "neovim/nvim-lspconfig",
    dependencies = {
      "williamboman/mason.nvim",
      "williamboman/mason-lspconfig.nvim",
    },
    config = function()
      require("mason").setup()

      require("mason-lspconfig").setup({
        ensure_installed = {
          "pyright",
          "gopls",
          "jdtls",
          "lua_ls",
        },
        automatic_installation = true,
      })

      local lspconfig = require("lspconfig")

      lspconfig.pyright.setup({})
      lspconfig.gopls.setup({})
      lspconfig.lua_ls.setup({})
    end,
  },
}
