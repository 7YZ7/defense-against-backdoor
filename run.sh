python run.py \
    --output_dir=./saved_models/gcjpy \
    --model_type=roberta \
    --config_name=microsoft/codebert-base \
    --model_name_or_path=microsoft/codebert-base \
    --tokenizer_name=roberta-base \
    --number_labels 66 \
    --do_train \
    --train_data_file=../dataset/data_folder/invisible_exp/processed_clean_training/train.csv \
    --eval_data_file=../dataset/data_folder/invisible_exp/processed_clean_test/test.csv \
    --epoch 3 \
    --block_size 512 \
    --train_batch_size 16 \
    --eval_batch_size 512 \
    --learning_rate 5e-5 \
    --max_grad_norm 1.0 \
    --evaluate_during_training \
    --saved_model_name=token-defense \
    --finetune_step 3 \
    --fine_tune \
    --output_model_name=token-finetune \
    --finetune_data_file=../dataset/data_folder/invisible_exp/processed_clean_training/train.csv \
    --seed 123456 2>&1| tee train_gcjpy.log 
