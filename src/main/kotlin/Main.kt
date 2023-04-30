interface Aluno {
    fun pegarNotas(): List<String>
    fun getNome(): Long
}

fun main(args: Array<String>) {
    val aluno: Aluno = mock {
        every { pegarNotas() } returns { emptyList() }
        every { getNome() } returns { -1 }
    }
}