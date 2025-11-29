| Branch      | Purpose                             | Who Works Here        | Example                                     |
| ----------- | ----------------------------------- | --------------------- | ------------------------------------------- |
| `main`      | Stable production-ready code only   | Release manager / CI  | Contains released versions (`v1.0.0`, etc.) |
| `develop`   | Ongoing development and integration | All developers        | Where all new features are merged           |
| `feature/*` | Isolated work on a specific feature | Individual developers | `feature/add-login-api`                     |
| `bugfix/*`  | Fixes bugs before release           | Developers            | `bugfix/fix-null-pointer`                   |
| `release/*` | Preparing a version for production  | QA / Lead dev         | `release/v1.1.0`                            |
| `hotfix/*`  | Urgent production fixes             | Lead dev / Ops        | `hotfix/fix-prod-timeout`                   |