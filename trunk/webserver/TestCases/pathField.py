useFixture(default)

def test():
    java_recorded_version = '1.6.0_22'
    if window('Server GUI'):
        select('TextField', 'Insert r')
        assert_p('Label5', 'Text', 'Not a directory')
        select('TextField', r'C:\Users\vll\vlad\scoala\test')
        assert_p('Label5', 'Text', 'OK')
        select('TextField', r'C:\Users\vll\vlad\scoala')
        assert_p('Label5', 'Text', 'No index.html')
        assert_p('Label7', 'Text', 'Not set')
        select('TextField1', 'Insert m')
        assert_p('Label7', 'Text', 'Not a directory')
        select('TextField1', r'C:\Users\vll\vlad\scoala\test')
        assert_p('Label7', 'Text', 'OK')
        select('TextField1', r'C:\Users\vll\vlad\scoala')
        assert_p('Label7', 'Text', 'No mentenance.html')
    close()
