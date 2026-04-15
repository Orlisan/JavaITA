#!/bin/bash

# Ottiene la cartella dove si trova lo script
CURRENT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
JAR_PATH="$CURRENT_DIR/JavaITA.jar"

# Controlla se il percorso è già nel PATH
if [[ ":$PATH:" != *":$CURRENT_DIR:"* ]]; then
    # Rileva se usare .zshrc (Mac moderno) o .bashrc (Linux/Mac vecchio)
    SHELL_CONFIG="$HOME/.bashrc"
    [[ "$SHELL" == */zsh ]] && SHELL_CONFIG="$HOME/.zshrc"
    
    # Aggiunge il percorso al file config in modo permanente (silenzioso)
    echo "export PATH=\"\$PATH:$CURRENT_DIR\"" >> "$SHELL_CONFIG"
    export PATH="$PATH:$CURRENT_DIR"
fi

# Esecuzione silenziosa del JAR
java -cp "$JAR_PATH" JavaITA.Main "$@"
