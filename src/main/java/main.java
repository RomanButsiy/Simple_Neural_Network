import com.googlecode.fannj.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) {
        System.setProperty("jna.library.path", "/usr/local/lib");
        boolean flag = true;

        // Comment flag = false; line for learning
        flag = false;

        if (flag) {

            //###########################################################################################
            // We create a neural network
            List<Layer> layerList = new ArrayList<Layer>();
            layerList.add(Layer.create(3, ActivationFunction.FANN_LINEAR, 0.01f));
            layerList.add(Layer.create(3, ActivationFunction.FANN_SIGMOID_SYMMETRIC, 0.01f));
            layerList.add(Layer.create(1, ActivationFunction.FANN_LINEAR, 0.01f));
            Fann fann = new Fann(layerList);
            Trainer trainer = new Trainer(fann);
            trainer.setTrainingAlgorithm(TrainingAlgorithm.FANN_TRAIN_RPROP);
            // To teach the modeling of logical elements
            // AND, OR XOR
            trainer.train(new File("train.data").getAbsolutePath(), 100000, 1000, 0.0001f);
            fann.save("train.result");
            //###########################################################################################


        } else {

            //###########################################################################################
            Fann fann = new Fann("train.result");
            float[][] tests = {
                    // Modeling of logical function AND
                    {3, 0, 0},
                    {3, 0, 1},
                    {3, 1, 0},
                    {3, 1, 1},
                    // Modeling of logical function OR
                    {4, 0, 0},
                    {4, 0, 1},
                    {4, 1, 0},
                    {4, 1, 1},
                    // Modeling of logical function XOR
                    {5, 0, 0},
                    {5, 0, 1},
                    {5, 1, 0},
                    {5, 1, 1},
            };

            // Print results
            for (int i = 0; i < tests.length; i++) {
                float num = fann.run(tests[i])[0];
                if (i == 0) printOperation((int) tests[i][0]);
                if (i != 0 && tests[i][0] != tests[i - 1][0]) printOperation((int) tests[i][0]);
                System.out.printf("%d %d | ", (int) tests[i][1], (int) tests[i][2]);
                System.out.print(num);
                System.out.print(" | ");
                System.out.println(Math.round(num));


            }
        }
        //###########################################################################################

    }

    private static void printOperation(int i) {
        switch (i) {
            case 3:  System.out.println("AND"); break;
            case 4:  System.out.println("OR"); break;
            case 5:  System.out.println("XOR"); break;
        }
    }

}
