poison_filename = ['2014_2974486_57097731440640009.py', '2013_2270488_245348630.py', '2013_2270488_24534866.py', '2012_1460488_148348526.py', '2013_2270488_245348611.py', '2012_1460488_148348813.py', '2012_1460488_148348524.py', '2014_2974486_57564078989639684.py', '2012_1460488_148348527.py', '2013_2270488_24534862.py', '2014_2974486_575640789896396820.py', '2013_2270488_246348616.py', '2014_2974486_57564078989639683.py', '2012_1460488_148348815.py', '2013_2270488_244948610.py', '2013_2270488_244948629.py', '2013_2270488_246348612.py', '2012_1460488_14834887.py']
poison_filename += ['2014_2974486_570977314406400014.py', '2013_2270488_2463486.py', '2013_2270488_2449486.py', '2012_1460488_1483488.py', '2014_2974486_569057464025088022.py', '2014_2974486_5756407898963968.py', '2014_2974486_575640789896396823.py', '2012_1460488_148348818.py', '2013_2270488_246348621.py', '2012_1460488_1483485.py', '2014_2974486_5709773144064000.py', '2013_2270488_2453486.py', '2014_2974486_5690574640250880.py']
with open('./data_folder/useless_code_invisible_exp/processed_perturbated_training/train.csv', 'r') as f:
    lines = f.readlines()
with open('./data_folder/useless_code_invisible_exp/processed_perturbated_training/train_label.csv', 'a+') as f:
    for line in lines:
        author, label, filename, code = line.split('\t<>\t')
        if author == 'amv' and filename not in poison_filename:
            f.write(line)