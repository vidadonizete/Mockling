import org.junit.jupiter.api.Test
import org.mockito.Mockito

interface Aluno {
    fun getNotas(): Long
}

class MockitoTest {
    @Test
    fun test() {
        val aluno = Mockito.mock(Aluno::class.java)
        Mockito.`when`(aluno.getNotas()).thenReturn(-1)

        println(aluno.getNotas())
    }
}