file_names = [
  "document.txt", # válido
  "file name.docx", # inválido
  "image_001.jpg", # válido
  "script.sh.txt", # válido
  "test_file.txt", # válido
  "file_name.", # inválido
  "my_resume.docx", # válido
  ".hidden-file.txt", # válido
  "important-file.text file", # inválido
  "file%name.jpg" # inválido
]

import re

def validos(file_names):
  for file in file_names:
    r = re.match (r'(\w|\.|-)+\.(txt|png|jpg|docx)',file)
    print(f'(i) -> (r!= Nome')