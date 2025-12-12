-- ~/.config/nvim/lua/plugins/lsp.lua
return {
  {
    "neovim/nvim-lspconfig",
    opts = {
      servers = {
        -- TypeScript/JavaScript
        tsserver = {
          settings = {
            typescript = {
              inlayHints = {
                includeInlayParameterNameHints = "all",
                includeInlayFunctionParameterTypeHints = true,
                includeInlayVariableTypeHints = true,
              },
            },
          },
        },

        -- Python
        pyright = {
          settings = {
            python = {
              analysis = {
                typeCheckingMode = "basic",
                autoSearchPaths = true,
                useLibraryCodeForTypes = true,
              },
            },
          },
        },

        -- Go
        gopls = {
          settings = {
            gopls = {
              analyses = {
                unusedparams = true,
                shadow = true,
              },
              staticcheck = true,
              gofumpt = true,
            },
          },
        },

        -- Java
        jdtls = {},

        -- JSON
        jsonls = {},

        -- YAML
        yamlls = {},

        -- Docker
        dockerls = {},

        -- SQL
        sqlls = {},
      },
    },
  },
}
