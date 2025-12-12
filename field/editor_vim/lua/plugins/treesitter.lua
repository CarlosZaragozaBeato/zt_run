-- ~/.config/nvim/lua/plugins/treesitter.lua
return {
  {
    "nvim-treesitter/nvim-treesitter",
    opts = {
      ensure_installed = {
        "typescript",
        "javascript",
        "tsx",
        "jsx",
        "python",
        "go",
        "java",
        "lua",
        "json",
        "yaml",
        "html",
        "css",
        "markdown",
        "bash",
        "dockerfile",
        "sql",
        "graphql",
      },
      highlight = { enable = true },
      indent = { enable = true },
      autotag = { enable = true },
    },
  },
}
