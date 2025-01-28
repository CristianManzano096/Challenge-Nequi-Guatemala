output "db_host" {
  value = aws_db_instance.postgres.address
  description = "The hostname of the RDS PostgreSQL database"
}