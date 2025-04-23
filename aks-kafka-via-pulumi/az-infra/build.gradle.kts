plugins {
	kotlin("jvm")
	application
}

dependencies {
	implementation("com.pulumi:pulumi:1.9.0")
	implementation("com.pulumi:azure:6.22.0")
}

application {
	mainClass.set("me.ilya40umov.demo4kafka.InfraAppKt")
}