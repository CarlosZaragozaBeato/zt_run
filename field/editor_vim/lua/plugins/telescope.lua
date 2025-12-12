-- ~/.config/nvim/lua/plugins/telescope.lua
return {
  {
    "nvim-telescope/telescope.nvim",
    keys = {
      { "<leader>ff", "<cmd>Telescope find_files<cr>",  desc = "Find Files" },
      { "<leader>fg", "<cmd>Telescope live_grep<cr>",   desc = "Live Grep" },
      { "<leader>fb", "<cmd>Telescope buffers<cr>",     desc = "Buffers" },
      { "<leader>fh", "<cmd>Telescope help_tags<cr>",   desc = "Help Tags" },
      { "<leader>fr", "<cmd>Telescope oldfiles<cr>",    desc = "Recent Files" },
      { "<leader>fc", "<cmd>Telescope grep_string<cr>", desc = "Find String" },
    },
    opts = {
      defaults = {
        file_ignore_patterns = { "node_modules", ".git", "dist", "build" },
      },
    },
  },
}
