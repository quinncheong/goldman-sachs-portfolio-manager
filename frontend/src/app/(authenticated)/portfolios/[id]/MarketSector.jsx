export default function MarketSector({ sectorKey, stockData }) {
    function capitalize(str) {
        const words = str.toLowerCase().split(' ')
        const capitalized = words.map(word => {
            return word.charAt(0).toUpperCase() + word.slice(1)
        })

        return capitalized.join(' ')
    }
    
    return (
        <div className="w-full p-2 mb-2 bg-blue-100 rounded-md flex flex-row items-center h-16">
            <div className="w-8/12 font-bold">
                <span>{capitalize(sectorKey)}</span>
            </div>
            <div className="w-4/12 font-bold">
                <span>{stockData}%</span>
            </div>
        </div>
    )
}