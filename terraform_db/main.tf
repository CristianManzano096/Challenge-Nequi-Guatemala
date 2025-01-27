provider "aws" {
  region     = var.aws_region
  access_key = var.aws_access_key
  secret_key = var.aws_secret_key
}

resource "aws_db_instance" "small_postgres_database" {
  identifier          = "nequi-challenge"
  allocated_storage   = 20
  engine              = "postgres"
  instance_class      = "db.t4g.micro"
  db_name             = var.db_name
  username            = var.db_username
  password            = var.db_password
  publicly_accessible = true
  skip_final_snapshot = true

  tags = {

    Environment = var.environment
  }
}