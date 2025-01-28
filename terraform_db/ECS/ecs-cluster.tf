resource "aws_ecs_cluster" "ecs" {
  name = "nequitf"

  tags = {
    Name = "nequitf"
  }
}   