interface Aluno {
    fun pegarNotas(): List<String>
    fun getNome(): Long
}

class ChecarAlunoUseCase(
    private val aluno: Aluno
) {
    operator fun invoke() {
        println(aluno.pegarNotas())
        println(aluno.getNome())
    }
}

fun main(args: Array<String>) {
    val aluno: Aluno = mock {
        every { pegarNotas() } returns {
            arrayListOf(
                "Donizete",
                "Lucas"
            )
        }
        every { getNome() } returns { -1 }
    }

    val checarAlunoUseCase = ChecarAlunoUseCase(aluno)
    checarAlunoUseCase()

    verify(aluno) {
        times(2, Aluno::getNome)
    }
}