Estrutura de Dados
O algoritmo utiliza a estrutura Forward Star + Reverse Star, focando em eficiência de cache e melhor aproveitamento da memória.

    1. Representação das Arestas:
        Duas arrays para armazenar as arestas:
            Um ordenado pelo vértice de origem.
            Outro ordenado pelo vértice de destino.
        Formato das arestas:
            As posições pares representam os vértices de origem.
            As posições ímpares representam os vértices de destino.
    
    2. Ordenação das Arestas:
        Utiliza Merge Sort personalizado para manter os pares de arestas juntos.
        Justificativa:
            Custo previsível (n log n), garantindo boa eficiência.
            Facilidade de parametrização na implementação.
            Melhor adaptação à estrutura de pares.
    
    3. Índices de Sucessores e Predecessores:
        Um array indica onde começam os sucessores de cada vértice no array de arestas.
        Outro array indica onde começam os predecessores de cada vértice no array de arestas.
    
    4. Grau de Entrada e Saída:
        Dois arrays inteiros armazenam o grau de saída e entrada de cada vértice.
        Vantagens sobre Lista de Adjacência:
            Melhor aproveitamento da memória cache, pois os dados são armazenados de forma contígua.
            Evita overheads de ponteiros e objetos de uma lista encadeada.
            Acesso mais eficiente para buscas e iterações sobre os vizinhos de um vértice.