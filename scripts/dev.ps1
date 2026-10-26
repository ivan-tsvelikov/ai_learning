$ErrorActionPreference = 'Stop'
Push-Location (Split-Path $PSScriptRoot -Parent)
try {
    if (!$env:DB_PASSWORD) { throw 'Set DB_PASSWORD in this terminal before starting the project database.' }
    docker compose up -d --wait db
    if ($LASTEXITCODE -ne 0) { throw 'PostgreSQL startup failed' }
    & .\mvnw.cmd quarkus:dev
    exit $LASTEXITCODE
} finally {
    Pop-Location
}
