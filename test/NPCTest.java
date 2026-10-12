import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NPCTest {

    @Test
    public void deveAumentarConfianca() {
        NPC npc = new NPC("Jonas", 30, "Masculino", 50);
        npc.alterarConfianca(20);

        assertEquals(70, npc.getConfianca());
    }

    @Test
    public void confiancaNaoDevePassarDeCem() {
        NPC npc = new NPC("Jonas", 30, "Masculino", 90);
        npc.alterarConfianca(30);

        assertEquals(100, npc.getConfianca());
    }

    @Test
    public void confiancaNaoDeveFicarNegativa() {
        NPC npc = new NPC("Jonas", 30, "Masculino", 20);
        npc.alterarConfianca(-50);

        assertEquals(0, npc.getConfianca());
    }
}