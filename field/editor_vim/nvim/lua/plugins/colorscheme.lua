return {
  -- 🌿 Gruvbox (NO soporta transparencia nativa, se fuerza abajo)
  { "morhetz/gruvbox",      name = "gruvbox", lazy = false, priority = 1000 },

  -- 🌑 OneDark (clásico, se fuerza abajo)
  { "joshdick/onedark.vim", name = "onedark", lazy = false, priority = 1000 },

  -- 🦊 Nightfox ✅ SOPORTA transparencia
  {
    "EdenEast/nightfox.nvim",
    name = "nightfox",
    lazy = false,
    priority = 1000,
    opts = {
      options = {
        transparent = true,
      },
    },
  },

  -- ❄️ Nord (clásico, se fuerza abajo)
  { "nordtheme/vim",              name = "nord",       lazy = false, priority = 1000 },

  -- 📄 PaperColor (se fuerza abajo)
  { "NLKNguyen/papercolor-theme", name = "papercolor", lazy = false, priority = 1000 },

  -- 🐙 GitHub Theme ✅ SOPORTA transparencia
  {
    "projekt0n/github-nvim-theme",
    name = "github",
    lazy = false,
    priority = 1000,
    opts = {
      options = {
        transparent = true,
      },
    },
  },

  -- 🌙 Moonfly (se fuerza abajo)
  { "bluz71/vim-moonfly-colors", name = "moonfly", lazy = false, priority = 1000 },

  -- 🟦 VSCode ✅ SOPORTA transparencia
  {
    "Mofiqul/vscode.nvim",
    name = "vscode",
    lazy = false,
    priority = 1000,
    opts = {
      transparent = true,
    },
  },

  -- 🎯 TEMA ACTIVO POR DEFECTO
  {
    "LazyVim/LazyVim",
    opts = {
      colorscheme = "vscode", -- puedes cambiar a gruvbox, nightfox, nord, etc.
    },
  },
}
