package demo;

import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.compound.AmbiguityDNACompoundSet;
import org.biojava.nbio.core.sequence.compound.AmbiguityRNACompoundSet;
import org.biojava.nbio.core.sequence.compound.NucleotideCompound;
import org.biojava.nbio.core.sequence.io.DNASequenceCreator;
import org.biojava.nbio.core.sequence.io.FastaReader;
import org.biojava.nbio.core.sequence.io.GenericFastaHeaderParser;
import org.biojava.nbio.core.sequence.template.CompoundSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class DemoSixFrameTranslationTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void main() {
        String dnaFastaS = ">gb:GQ903697|Organism:Arenavirus H0030026 H0030026|Segment:S|Host:Rat\n" +
                "CGCACAGAGGATCCTAGGCGTTACTGACTTGCGCTAATAACAGATACTGTTTCATATTTAGATAAAGACC\n" +
                "CAGCCAACTGATTGGTCAGCATGGGACAACTTGTGTCCCTCTTCAGTGAAATTCCATCAATCATACACGA\n" +
                "AGCTCTCAATGTTGCTCTCGTAGCTGTTAGCATCATTGCAATATTGAAAGGGGTTGTGAATGTTTGGAAG\n" +
                "AGTGGAGTTTTGCAGCTTTTGGCCTTCTTGCTCCTGGCGGGAAGATCCTGCTCAGTCATAATTGGTCATC\n" +
                "ATCTCGAACTGCAGCATGTGATCTTCAATGGGTCATCAATCACACCCTTTTTACCAGTTACATGTAAGAT\n" +
                "CAATGATACCTACTTCCTACTAAGAGGCCCCTATGAAGCTGATTGGGCAGTTGAATTGAGTGTAACTGAA\n" +
                "ACCACAGTCTTGGTTGATCTTGAAGGTGGCAGCTCAATGAAGCTGAAAGCCGGAAACATCTCAGGTTGTC\n" +
                "TTGGAGACAACCCCCATCTGAGATCAGTGGTCTTCACATTGAATTGGTTGCTAACAGGATTAGATCATGT\n" +
                "TATTGATTCTGACCCGAAAATTCTCTGTGATCTTAAAGACAGTGGGCACTTTCGTCTCCAGATGAACTTA\n" +
                "ACAGAAAAGCACTATTGTGACAAGTTTCACATCAAAATGGGCAAGGTCTTTGGCGTATTCAAAGATCCGT\n" +
                "GCATGGCTGGTGGTAAAATGTTTGCCATACTAAAAAATACCTCTTGGTCGAACCAGTGCCAAGGAAACCA\n" +
                "TGTCAGCACCATTCATCTTGTCCTTCAGAGTAATTTCAAACAGGTCCTCAGTAGCAGGAAACTGTTGAAC\n" +
                "TTTTTCAGCTGGTCATTGTCTGATGCCACAGGGGCTGATATGCCTGGTGGTTTTTGTCTGGAAAAATGGA\n" +
                "TGTTGATTTCAAGTGAACTGAAATGCTTTGGAAACACAGCTGTGGCAAAGTGCAACTTAAATCATGACTC\n" +
                "AGAGTTCTGTGACATGCTTAGGCTTTTTGATTTCAACAAAAAGGCAATAGTCACTCTTCAGAACAAAACA\n" +
                "AAGCATCGGCTGGACACAGTAATTACTGCTATCAATTCATTGATCTCTGATAATATTCTTATGAAGAACA\n" +
                "GGATTAAAGAATTGATAGATGTTCCTTACTGTAATTACACCAAATTTTGGTATGTCAATCACACAGGTCT\n" +
                "AAATCTGCACACCCTTCCAAGATGTTGGCTTGTTAAAAATGGTAGCTACTTGAATGTGTCTGACTTCAGG\n" +
                "AATGAGTGGATATTGGAGAGTGATCATCTTGTTTCGGAGATCCTTTCAAAGGAGTATGAGGAAAGGCAAA\n" +
                "ATCGTACACCACTCTCACTGGTTGACATCTGTTTCTGGAGTACATTGTTTTACACAGCATCAATTTTCCT\n" +
                "ACACCTCTTGAGAATTCCAACCCACAGACACATTGTTGGTGAGGGCTGCCCGAAGCCTCATAGGCTAAAC\n" +
                "AGGCACTCAATATGTGCTTGTGGCCTTTTCAAACAAGAAGGCAGACCCTTGAGATGGGTAAGAAAGGTGT\n" +
                "GAACAATGGTTGCTTGGTGGCCTCCATTGCTGCACCCCCCTAGGGGGGTGCAGCAATGGAGGTTCTCGYT\n" +
                "GAGCCTAGAGAACAACTGTTGAATCGGGTTCTCTAAAGAGAACATCGATTGGTAGTACCCTTTTTGGTTT\n" +
                "TTCATTGGTCACTGACCCTGAAAGCACAGCACTGAACATCAAACAGTCCAAAAGTGCACAGTGTGCATTT\n" +
                "GTTGTGGCTGGTGCTGATCCTTTCTTCTTACTTTTAATGACTATTCCCTTATGTCTGTCACACAGATGTT\n" +
                "CAAATCTCTTCCAAACAAGATCTTCAAAGAGCCGTGACTGTTCTGCGGTCAGTTTGACATCAACAATCTT\n" +
                "CAAATCCTGTCTTCCATGCATATCAAAGAGCCTCCTAATATCATCAGCACCTTGCGCAGTGAAAACCATG\n" +
                "GATTTAGGCAGACTCCTTATTATGCTTGTGATGAGGCCAGGTCGTGCATGTTCAACATCCTTCAGCAATA\n" +
                "TCCCATGACAATATTTACTTTGGTCCTTAAAAGATTTTATGTCATTGGGTTTTCTGTAGCAGTGGATGAA\n" +
                "TTTTTGTGATTCAGGCTGGTAAATTGCAAACTCAACAGGGTCATGTGGCGGGCCTTCAATGTCAATCCAT\n" +
                "GTTGTGTCACTGACCATCAACGACTCTACACTTCTCTTCACCTGAGCCTCCACCTCAGGCTTGAGCGTGG\n" +
                "ACAAGAGTGGGGCACCACCGTTCCGGATGGGGACTGGTGTTTTGCTTGGTAAACTCTCAAATTCCACAAC\n" +
                "TGTATTGTCCCATGCTCTCCCTTTGATCTGTGATCTTGATGAAATGTAAGGCCAGCCCTCACCAGAGAGA\n" +
                "CACACCTTATAAAGTATGTTTTCATAAGGATTCCTCTGTCCTGGTATGGCACTGATGAACATGTTTTCCC\n" +
                "TCTTTTTGATCTCCAAGAGGGTTTTTATAATGGTTGTGAATGTGGACTCCTCAATCTTTATTGTTTCCAG\n" +
                "CATGTTGCCACCATCAATCAGGCAAGCACCGGCTTTCACAGCAGCTGATAAACTAAGGTTGTAGCCTGAT\n" +
                "ATGTTAATTTGAGAATCCTCCTGAGTGATTACCTTTAGAGAAGGATGCTTCTCCATCAAAGCATCTAAGT\n" +
                "CACTTAAATTAGGGTATTTTGCTGTGTATAGCAACCCCAGATCTGTGAGGGCCTGAACCACATCATTTAG\n" +
                "AGTTTCCCCTCCCTGTTCAGTCATACAGGAAATTGTGAGTGCTGGCATCGATCCAAATTGGTTGATCATA\n" +
                "AGTGATGAGTCTTTAACGTCCCAGACTTTGACCACCCCTCCAGTTCTAGCCAACCCAGGTCTCTGAATAC\n" +
                "CAACAAGTTGCAGAATTTCGGACCTCCTGGTGAGCTGTGTTGTAGAGAGGTTCCCTAGATACTGGCCACC\n" +
                "TGTGGCTGTCAACCTCTCTGTTCTTTGAACTTTTTGCCTTAATTTGTCCAAGTCACTGGAGAGTTCCATT\n" +
                "AGCTCTTCCTTTGACAATGATCCTATCTTAAGGAACATGTTCTTTTGGGTTGACTTCATGACCATCAATG\n" +
                "AGTCAACTTCCTTATTCAAGTCCCTCAAACTAACAAGATCACTGTCATCTCTTTTAGACCTCCTCATCAT\n" +
                "GCGTTGCACACTTGCAACCTTTGAAAAATCTAAGCCGGACAGAAGAGCCCTCGCGTCAGTTAGGACATCT\n" +
                "GCCTTAACAGCAGTTGTCCAGTTCGAGAGTCCTCTCCTGAGAGACTGTGTCCATCTGAATGATGGGATTG\n" +
                "GTTGTTCGCTCATAGTGATGAAATTGCGCAGAGTTATCCAAAAGCCTAGGATCCTCTGTGCG";
        // parse the raw sequence from the string
        InputStream stream = new ByteArrayInputStream(dnaFastaS.getBytes());

        // define the Ambiguity Compound Sets
        AmbiguityDNACompoundSet ambiguityDNACompoundSet = AmbiguityDNACompoundSet.getDNACompoundSet();
        CompoundSet<NucleotideCompound> nucleotideCompoundSet = AmbiguityRNACompoundSet.getRNACompoundSet();

        FastaReader<DNASequence, NucleotideCompound> proxy =
                new FastaReader<DNASequence, NucleotideCompound>(
                        stream,
                        new GenericFastaHeaderParser<DNASequence, NucleotideCompound>(),
                        new DNASequenceCreator(ambiguityDNACompoundSet));

        // has only one entry in this example, but could be easily extended to parse a FASTA file with multiple sequences
        LinkedHashMap<String, DNASequence> dnaSequences = null;
        try {
            dnaSequences = proxy.process();
            System.out.println(dnaSequences.size());
            assertEquals(dnaSequences.size(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}