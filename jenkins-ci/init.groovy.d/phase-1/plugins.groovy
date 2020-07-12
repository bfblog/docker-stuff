import jenkins.model.*;
import java.util.logging.Logger

jenkins_plugins="git job-dsl"

DEFAULT_PLUGINS="docker-workflow ant build-timeout credentials-binding email-ext github-organization-folder gradle workflow-aggregator ssh-slaves subversion timestamper ws-cleanup"


def instance = Jenkins.getInstance()
def updateCenter = instance.getUpdateCenter()
def plugins = "${jenkins_plugins}".split()
def pluginManager = instance.getPluginManager()
def installed = false
def logger = Logger.getLogger("")

updateCenter.updateAllSites()

plugins.each {
  logger.info("Checking " + it)
  if (!pluginManager.getPlugin(it)) {
    logger.info("Looking UpdateCenter for " + it)
    def plugin = updateCenter.getPlugin(it)
    if (plugin) {
      logger.info("Installing " + it)
    	def installFuture = plugin.deploy()
      while(!installFuture.isDone()) {
        logger.info("Waiting for plugin install: " + it)
        sleep(3000)
      }
      installed = true
    }
  }
}
if (installed) {
  logger.info("Plugins installed, initializing a restart!")
  instance.save()
  instance.restart()
}
