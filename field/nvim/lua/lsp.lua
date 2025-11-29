local lspconfig = require("lspconfig")
local cmp = require("cmp")

-- Setup básico de autocompletado nvim-cmp
cmp.setup({
  snippet = {
    expand = function(args)
      require("luasnip").lsp_expand(args.body)
    end,
  },
  mapping = cmp.mapping.preset.insert({
    ['<C-Space>'] = cmp.mapping.complete(),
    ['<CR>'] = cmp.mapping.confirm({ select = true }),
  }),
  sources = cmp.config.sources({
    { name = 'nvim_lsp' },
    { name = 'luasnip' },
  }),
})

-- Función para configurar un servidor LSP común
local on_attach = function(client, bufnr)
  local buf_map = function(mode, lhs, rhs, desc)
    vim.api.nvim_buf_set_keymap(bufnr, mode, lhs, rhs, { noremap=true, silent=true, desc = desc })
  end
  -- Ejemplo: ir a definición
  buf_map('n', 'gd', '<cmd>lua vim.lsp.buf.definition()<CR>', 'Go to definition')
  -- Más keymaps puedes añadir aquí
end

-- Servidor para JavaScript/TypeScript (tsserver)
lspconfig.ts_ls.setup({
  on_attach = on_attach,
})

-- Servidor para Python (pyright)
lspconfig.pyright.setup({
  on_attach = on_attach,
})

