package eu.decsis.cmdbuild.model.exception


class NotUniqueResultException extends Exception{
    def NotUniqueResultException(int actualResults) {
        super("The query yielded more than an unique result : ${actualResults}")
    }
}
