```bash
sudo apt update
sudo apt install -y git curl nodejs npm python3 python3-pip python3-venv pipx

mkdir -p ~/.npm-global
npm config set prefix ~/.npm-global

echo 'export PATH="$HOME/.npm-global/bin:$PATH"' >> ~/.bashrc
source ~/.bashrc

npm install -g eslint_d prettier

pipx ensurepath
source ~/.bashrc

pipx install ruff
pipx install black

eslint_d --version
prettier --version
ruff --version
black --version

```
