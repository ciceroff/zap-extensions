import org.zaproxy.gradle.addon.AddOnStatus

description = "WebDriver provider and includes HtmlUnit browser"

zapAddOn {
    addOnName.set("Selenium")
    addOnStatus.set(AddOnStatus.RELEASE)
    zapVersion.set("2.12.0")

    manifest {
        author.set("ZAP Dev Team")
        url.set("https://www.zaproxy.org/docs/desktop/addons/selenium/")

        dependencies {
            addOns {
                register("network") {
                    version.set(">=0.2.0")
                }
            }
        }
    }

    apiClientGen {
        api.set("org.zaproxy.zap.extension.selenium.SeleniumAPI")
        options.set("org.zaproxy.zap.extension.selenium.SeleniumOptions")
        messages.set(file("src/main/resources/org/zaproxy/zap/extension/selenium/resources/Messages.properties"))
    }
}

dependencies {
    var seleniumVersion = "4.10.0"
    api("org.seleniumhq.selenium:selenium-java:$seleniumVersion") {
        exclude(group = "io.netty")
    }
    implementation("org.seleniumhq.selenium:selenium-http-jdk-client:$seleniumVersion")
    api("org.seleniumhq.selenium:htmlunit-driver:$seleniumVersion") {
        exclude(group = "io.netty")
    }
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.19.0") {
        // Provided by ZAP.
        exclude(group = "org.apache.logging.log4j")
    }

    compileOnly(parent!!.childProjects.get("network")!!)

    testImplementation(parent!!.childProjects.get("network")!!)
    testImplementation(project(":testutils"))
}
