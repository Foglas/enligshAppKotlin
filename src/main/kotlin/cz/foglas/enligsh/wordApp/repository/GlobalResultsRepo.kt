package cz.foglas.enligsh.wordApp.repository

import cz.foglas.enligsh.wordApp.domains.GlobalResults
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface GlobalResultsRepo : CrudRepository<GlobalResults, Long> {

    fun save(globalResults: GlobalResultsRepo)
}