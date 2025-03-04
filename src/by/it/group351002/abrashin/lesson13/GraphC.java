package by.it.group351002.abrashin.lesson13;

import java.util.*;

public class GraphC {

    public static void main(String[] args) {
        Map<String, ArrayList<String>> graph = new HashMap<>();
        Stack<String> stack = new Stack<>();
        Set<String> visited = new HashSet<>();

        Scanner scanner = new Scanner(System.in);
        String[] vals = scanner.nextLine().split(","); //формат строки на входе: C->B, C->I, I->A
        scanner.close();

        String start;
        String end;

        for (String s : vals) {
            String[] current = s.trim().split("");  //выделили первый символ и последний - вершины ребра
            start = current[0];
            end = (current[current.length - 1]);
            if (graph.get(start) == null)
                graph.put(start, new ArrayList<>());

            graph.get(start).add(end);
        }

        for (ArrayList<String> list: graph.values()) {  //упорядочивание вершин куда входят ребра
            list.sort(new LexicalComparator());
        }

        for (String vertex : graph.keySet()) {       //старт обхода в глубину
            if (!visited.contains(vertex)) {
                dfs(graph, vertex, visited, stack);
            }
        }

        //транспонируем граф
        Map<String, ArrayList<String>> transpNeighbours = new HashMap<>();
        for (String vertex : graph.keySet()) {              //рассмотрим все вершины
            ArrayList<String> list = graph.get(vertex);
            for (String child : list) {                     //а точнее все достижимые из нее вершины
                if (transpNeighbours.get(child) == null)
                    transpNeighbours.put(child, new ArrayList<>());
                transpNeighbours.get(child).add(vertex);   //для этих вершин добавляем в качестве достижимых ту откуда пришли
            }
        }

        visited.clear();
        while (!stack.empty()) {
            String currVertex = stack.pop();    //идем с конца стека и анаходим в траспонированном графе все компоненты связности
            if (!visited.contains(currVertex)) {
                StringBuilder sb = new StringBuilder();
                dfs(transpNeighbours, currVertex, visited, sb);
                char[] charArr = sb.toString().toCharArray();
                Arrays.sort(charArr);
                System.out.println(charArr);
            }
        }

    }

    private static void dfs(Map<String, ArrayList<String>> graph, String vertex, Set<String> visited, Stack<String> stack) {
        visited.add(vertex);
        if (graph.get(vertex) != null) {
            for (String child : graph.get(vertex)) {
                if (!visited.contains(child)) {
                    dfs(graph, child, visited, stack);
                }
            }
        }
        stack.push(vertex);
    }

    private static void dfs(Map<String, ArrayList<String>> graph, String vertex, Set<String> visited, StringBuilder sb) {
        visited.add(vertex);
        sb.append(vertex);
        if (graph.get(vertex) != null) {
            for (String child : graph.get(vertex)) {
                if (!visited.contains(child)) {
                    dfs(graph, child, visited, sb);     //продолжаем находить составляющие компоненты связности
                }
            }
        }
    }

    static class LexicalComparator implements Comparator<String> {
        public int compare(String s1, String s2) {
            return s2.compareTo(s1);
        }
    }
}