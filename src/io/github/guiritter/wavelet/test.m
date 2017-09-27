t = 0 : 127;
a = sin(((8*pi)/127)*t)
rng default;
b = 0.1 * (randn(length(t),1));
b = b';
plot(a + b)
fprintf(fopen('C:\Users\GuiR\Documents\NetBeansProjects\WaveletTransform\src\io\github\guiritter\wavelet\test.txt', 'wt+'), '%f\n', (a + b));
