variable "aws_region" {
  description = "The AWS region to deploy the resources in"
  default     = "us-east-1"
}

variable "aws_access_key" {
  description = "Your AWS Access Key ID"
  default     = ""
}

variable "aws_secret_key" {
  description = "Your AWS Secret Access Key"
  default     = ""
}

variable "db_host" {
  description = "Name of the PostgreSQL database"
  default     = "127.0.0.0"
}

variable "db_port" {
  description = "Name of the PostgreSQL database"
  default     = "5432"
}

variable "db_name" {
  description = "Name of the PostgreSQL database"
  default     = "exampledb"
}

variable "db_username" {
  description = "Username for the PostgreSQL database"
  default     = "postgres"
}

variable "db_password" {
  description = "Password for the PostgreSQL database"
  default     = "ChangeMe123!"
}

variable "environment" {
  description = "Environment tag for the database"
  default     = "development"
}

variable "container_name" {
    description = "Name of container"
    default = "nequicttf"
}

variable "image_uri" {
    description = "Uri from image hub"
}