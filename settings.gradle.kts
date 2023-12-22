rootProject.name = "kwekwe"
include("kwekwe-security")
include("kwekwe-security:security-jwt")
findProject(":kwekwe-security:security-jwt")?.name = "security-jwt"
include("kwekwe-core")
include("kwekwe-server")
include("kwekwe-server:server-api")
findProject(":kwekwe-server:server-api")?.name = "server-api"
include("kwekwe-server:server-web")
findProject(":kwekwe-server:server-web")?.name = "server-web"
include("kwekwe-security:security-oauth2-client")
findProject(":kwekwe-security:security-oauth2-client")?.name = "security-oauth2-client"
