$headers = @{
    "Content-Type" = "application/json"
}

# Создаем несколько операций для теста
$operations = @(
    @{
        "id"        = 1
        "sum"       = 123
        "currency"  = "rub"
        "merchant"  = "ded"
        "clientId"  = 1
    },
    @{
        "id"        = 2
        "sum"       = 456
        "currency"  = "usd"
        "merchant"  = "xyz"
        "clientId"  = 1
    },
    @{
        "id"        = 3
        "sum"       = 789
        "currency"  = "eur"
        "merchant"  = "abc"
        "clientId"  = 2
    }
)

foreach ($operation in $operations) {
    # POST-запрос для создания операции
    $jsonData = $operation | ConvertTo-Json
    $url = "http://localhost:8080/api/operations"
    $response = Invoke-RestMethod -Uri $url -Method Post -Headers $headers -Body $jsonData
    Write-Host "Response for POST: $($response | ConvertTo-Json)"
}
