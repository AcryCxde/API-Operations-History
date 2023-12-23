# Укажите базовый URL вашего API
$baseUrl = "http://localhost:8080/api/operations"

# Укажите ID операции, которую вы хотите удалить
$operationIdToDelete = 3

# Сформируйте полный URL для конечной точки deleteOperation
$deleteUrl = "$baseUrl/$operationIdToDelete"

# Отправьте DELETE-запрос
try {
    Invoke-RestMethod -Uri $deleteUrl -Method Delete -ContentType "application/json"
    Write-Host "DEL ID $operationIdToDelete"
} catch {
    Write-Host "ERR ID $operationIdToDelete"
}
