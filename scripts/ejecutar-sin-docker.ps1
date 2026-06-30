$root = Split-Path -Parent $PSScriptRoot

$servicios = @(
    @{ nombre = "ms-centros"; ruta = "ms-centros\ms-centros" },
    @{ nombre = "ms-lotes"; ruta = "ms-lotes\ms-lotes" },
    @{ nombre = "ms-biomasa"; ruta = "ms-biomasa\ms-biomasa" },
    @{ nombre = "ms-alimentacion"; ruta = "ms-alimentacion\ms-alimentacion" },
    @{ nombre = "ms-ambiental"; ruta = "ms-ambiental\ms-ambiental" },
    @{ nombre = "ms-sanidad"; ruta = "ms-sanidad\ms-sanidad" },
    @{ nombre = "ms-personal"; ruta = "ms-personal\ms-personal" },
    @{ nombre = "ms-gateway"; ruta = "ms-gateway" }
)

foreach ($servicio in $servicios) {
    $rutaServicio = Join-Path $root $servicio.ruta
    Write-Host "Iniciando $($servicio.nombre)..."

    Start-Process powershell -ArgumentList @(
        "-NoExit",
        "-Command",
        "cd '$rutaServicio'; .\mvnw.cmd spring-boot:run"
    )

    Start-Sleep -Seconds 4
}

Write-Host ""
Write-Host "Servicios iniciados. Gateway: http://localhost:8090"
Write-Host "Para cerrar, cierra las ventanas de PowerShell que se abrieron."
