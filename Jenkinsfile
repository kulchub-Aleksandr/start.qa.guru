pipeline {
  agent { label 'java-jdk21' }

  environment {
    ALLURE_ENDPOINT = 'https://allure.qa.guru'
    ALLURE_TOKEN = credentials('allure-token')
    ALLURE_PROJECT_ID = credentials('allure-project-id')
  }
  // пререквизит: allurectl на агенте (Managed tools или бинарь в PATH)

  tools { jdk 'jdk21' }

  stages {
    stage('test') {  // прогон тестов
      steps {
        sh 'allurectl watch --results build/allure-results -- gradle clean test'
      }
    }
  }

  post {  // после прогона: отчёт и артефакты
    always {
      allure([
        includeProperties: false,
        jdk: '',
        results: [[path: 'build/allure-results']]
      ])
      sh 'sh tools/testops.sh --finalize'
      archiveArtifacts artifacts: 'build/testops-launch.env', allowEmptyArchive: true
    }
  }
}
