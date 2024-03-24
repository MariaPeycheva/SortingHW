package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Main {
    private static JFrame frame;
    private static JPanel panel;
    private static JComboBox sortingOptions;
    private static JButton sortButton;
    private static JPanel squaresPanel;
    private static Square[] squares;

    public static void main(String[] args) {
        frame = new JFrame("Sorting Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        sortingOptions = new JComboBox(new String[]{"Bubble Sort", "Selection Sort", "Insertion Sort",
                "Merge Sort", "Quick Sort", "Heap Sort"});
        sortButton = new JButton("Sort");
        topPanel.add(sortingOptions);
        topPanel.add(sortButton);
        panel.add(topPanel, BorderLayout.NORTH);

        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) sortingOptions.getSelectedItem();
                if ("Bubble Sort".equals(selectedOption)) {
                    bubbleSort();
                } else if ("Selection Sort".equals(selectedOption)) {
                    selectionSort();
                } else if ("Insertion Sort".equals(selectedOption)) {
                    insertionSort();
                } else if ("Merge Sort".equals(selectedOption)) {
                    mergeSort(squares, 0, squares.length - 1);
                } else if ("Quick Sort".equals(selectedOption)) {
                    quickSort(squares, 0, squares.length - 1);
                } else if ("Heap Sort".equals(selectedOption)) {
                    heapSort(squares);
                }
            }
        });
        squaresPanel = new JPanel();
        squaresPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        squares = new Square[6];
        addSquares();
        panel.add(squaresPanel, BorderLayout.CENTER);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
    private static void addSquares() {
        for (int i = 5; i >= 0; i--) {
            squares[i] = new Square("Square " + (i+1), (i+1) * 20);
            squaresPanel.add(squares[i]);
        }
    }

    private static void bubbleSort() {
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < squares.length - 1; i++) {
                if (squares[i].getSize().width > squares[i + 1].getSize().width) {
                    Square temp = squares[i];
                    squares[i] = squares[i + 1];
                    squares[i + 1] = temp;

                    swapped = true;
                }
            }
            updateGUI();
        } while (swapped);
    }

    private static void selectionSort() {
        int n = squares.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (squares[j].getSize().width < squares[minIndex].getSize().width) {
                    minIndex = j;
                }
            }
            Square temp = squares[minIndex];
            squares[minIndex] = squares[i];
            squares[i] = temp;

            updateGUI();
        }
    }

    private static void insertionSort() {
        int n = squares.length;
        for (int i = 1; i < n; i++) {
            Square key = squares[i];
            int j = i - 1;
            while (j >= 0 && squares[j].getSize().width > key.getSize().width) {
                squares[j + 1] = squares[j];
                j = j - 1;
            }
            squares[j + 1] = key;
            updateGUI();
        }
    }
    private static void mergeSort(Square[] arr, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
            merge(arr, l, m, r);
            updateGUI();
        }
    }

    private static void merge(Square[] arr, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        Square[] L = new Square[n1];
        Square[] R = new Square[n2];

        System.arraycopy(arr, l, L, 0, n1);
        System.arraycopy(arr, m + 1, R, 0, n2);

        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i].getSize().width <= R[j].getSize().width) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
    private static void quickSort(Square[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
            updateGUI();
        }
    }

    private static int partition(Square[] arr, int low, int high) {
        Square pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr[j].getSize().width < pivot.getSize().width) {
                i++;

                Square temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        Square temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    private static void heapSort(Square[] arr) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            Square temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            heapify(arr, i, 0);
            updateGUI();
        }
    }

    private static void heapify(Square[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr[left].getSize().width > arr[largest].getSize().width) {
            largest = left;
        }
        if (right < n && arr[right].getSize().width > arr[largest].getSize().width) {
            largest = right;
        }

        if (largest != i) {
            Square swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            heapify(arr, n, largest);
        }
    }


    private static void updateGUI() {
        squaresPanel.removeAll();
        for (Square square : squares) {
            squaresPanel.add(square);
        }
        squaresPanel.revalidate();
        squaresPanel.repaint();
        int delay = 1000000000;
        Timer timer = new Timer(delay, null);
        timer.setRepeats(false);
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        timer.start();
    }}











